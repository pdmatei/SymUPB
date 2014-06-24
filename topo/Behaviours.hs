module Behaviours where

-- a relation between types a and b. It enumerates the operations which can be applied on elements of type a and containers of type b 
class ToContainer a b where
    make :: a -> b  -- makes a compactflow out of values of this type
    (.>.) :: a -> b -> b -- adds to an existing compactflow, a value of this type
    infixr .>.
    (.\.) :: b -> a -> b -- removes from an existing compactflow, a value of this type
    infixl .\.
    (.@.) :: a -> b -> Bool -- checks if an element of type a is contained in a container of type b
    infixl .@.
    (.|.) :: b -> a -> b

--class Unifiable where 

class Ops a where
    cap :: a -> a -> a
