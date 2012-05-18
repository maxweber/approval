(ns approval.ring-middleware
  (:use approval.core
        ring.util.response))

(defn wrap-approval
  ([handler get-approval prefix query-param]
     (wrap-approval handler get-approval nil prefix query-param))
  ([handler get-approval method prefix query-param]
     (fn [req]
       (if (and (= prefix (:uri req))
                (or (not method) (= method (:request-method req))))
         (if-let [token (get-in req [:query-params query-param])]
           (if-let [[f data] (approve get-approval token)]
             (f data req)
             (status (response "") 404))
           (status (response "") 400))
         (handler req)))))
