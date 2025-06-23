(defn operator [func]
  (fn [& args]
    (fn [vars]
      (apply func (map (fn [arg] (arg vars)) args)))))

(defn my-divide
  ([x] (/ 1.0 (double x)))
  ([x & xs] (reduce (fn [a b] (/ (double a) (double b))) (double x) xs)))

(def add (operator +))
(def subtract (operator -))
(def multiply (operator *))
(def divide (operator my-divide))
(def arcSinh (operator (fn [x] (Math/log (+ x (Math/sqrt (+ (* x x) 1)))))))
(def arcCosh (operator (fn [x] (Math/log (+ x (Math/sqrt (- (* x x) 1)))))))

(defn variable [name]
  (fn [vars]
    (get vars name)))

(def negate (operator -)) 

(defn constant [num] (constantly num))

(def operations {
  '+ add
  '- subtract
  '* multiply
  '/ divide
  'negate negate
  'asinh arcSinh
  'acosh arcCosh
})

(defn parse [s]
  (cond
    (number? s) (constant s)
    (symbol? s) (variable (name s))
    (list? s) (apply (get operations (first s)) 
                       (map parse (rest s)))))

(defn parseFunction [expr]
  (parse (read-string expr)))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; :NOTE: const/var можно объединить
(defn Constant [val]
  {:val val
   :prototype
   {:evaluate (fn [this _] (:val this))
    :toString (fn [this] (str (:val this)))}})

(defn Variable [name]
  {:name name
   :prototype
   {:evaluate (fn [this vars] (get vars (:name this)))
    :toString (fn [this] (:name this))}})

(defn proto-call [this key & args]
  (apply (get (:prototype this) key) this args))

(defn abstrOperator [func symbol]
  (fn [& args]
    {:args args
     :prototype
     {:evaluate (fn [this vars]
                  (apply func (map (fn [arg] (proto-call arg :evaluate vars)) (:args this))))
      :toString (fn [this]
                  (str "(" symbol (apply str (map (fn [arg] (str " " (proto-call arg :toString))) (:args this))) ")"))}}))

(def evaluate (fn [this vars] ((:evaluate (:prototype this)) this vars)))
(def toString (fn [this] ((:toString (:prototype this)) this)))

(defn cube [num] (* num num num))


(def Add (abstrOperator + "+"))
(def Subtract (abstrOperator - "-"))
(def Multiply (abstrOperator * "*"))
(def Divide (abstrOperator my-divide "/"))
(def Negate (abstrOperator - "negate"))
(def Cube (abstrOperator cube "cube"))
(def Cbrt (abstrOperator Math/cbrt "cbrt"))


(def Ooperations {
  '+ Add
  '- Subtract
  '* Multiply
  '/ Divide
  'negate Negate
  'cube Cube
  'cbrt Cbrt
})

(defn subParse [s]
  (cond
    (number? s) (Constant s)
    (symbol? s) (Variable (name s))
    (list? s) (apply (get Ooperations (first s)) 
                     (map subParse (rest s)))))

(defn parseObject [expr]
  (subParse (read-string expr)))