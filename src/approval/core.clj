(ns approval.core)

(defn generate-token []
  (str (java.util.UUID/randomUUID)))

(defn create-approval [token function-key data]
  {:token token
   :function function-key
   :data data})

(defmulti approved-function
  identity)

(defn approve [get-approval token]
  (when-let [approval (get-approval token)]
    [(approved-function (:function approval))
     (:data approval)]))
