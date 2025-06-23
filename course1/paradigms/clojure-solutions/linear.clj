(defn operator [f] (fn [a b] (if (number? a) (f a b) (mapv (operator f) a b))))

(def v+ (operator +))
(def v- (operator -))
(def v* (operator *))
(def vd (operator /))

(defn scalar [v1 v2] (reduce + (mapv * v1 v2)))

(defn vect [v1, v2]
  ((fn [a b]
      (letfn [(f [x y] (- (* (nth a x) (nth b y)) (* (nth a y) (nth b x))))]
        [(f 1 2) (f 2 0) (f 0 1)])) v1 v2))

(defn v*s [v s] (mapv (fn [x] (* x s)) v))

(def m+ (operator v+))
(def m- (operator v-))
(def m* (operator v*))
(def md (operator vd))

(defn m*s [m s] (mapv (fn [x] (v*s x s)) m))

(defn transpose [m] (apply mapv vector m))

(defn m*v [m v] (mapv (fn [a] (scalar a v)) m))

(defn m*m [m1 m2] (mapv (fn [row] (m*v (transpose m2) row)) m1))

(def t+ (operator +))
(def t- (operator -))
(def t* (operator *))
(def td (operator /))
