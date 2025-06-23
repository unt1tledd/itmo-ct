prime(N) :-
    N > 1,
    \+ has_div(N, 2).

has_div(N, D) :-
    D * D =< N,
    (N mod D =:= 0; has_div(N, D + 1)).

composite(N) :-
    N > 1,
    has_div(N, 2).

prime_divisors(1, []).

prime_divisors(N, Divisors) :-
    N > 1,
    find_div(N, 2, Divisors).

find_div(1, _, []).

find_div(N, D, [D | Divisors]) :-
    N mod D =:= 0,
    N1 is N // D,
    find_div(N1, D, Divisors).

find_div(N, D, Divisors) :-
    N mod D =\= 0,
    D * D < N,
    D1 is D + 1,
    find_div(N, D1, Divisors).

find_div(N, D, [N]) :-
    N mod D =\= 0,
    D * D >= N,
    N > 1.

unique([], []).
unique([H | T], R) :-
    member(H, T), !,
    unique(T, R).
unique([H | T], [H | R]) :-
    unique(T, R).


count(1, 0).
count(N, M) :-
    N > 1,
    prime_divisors(N, D),
    unique(D, Unique),
    length(Unique, M).