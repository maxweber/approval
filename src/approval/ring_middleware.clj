(ns approval.ring-middleware
  (:use approval.core
        ring.util.response))

(defn wrap-approval [handler get-approval prefix query-param]
  (fn [req]
    (if (= prefix (:uri req))
      (if-let [token (get-in req [:query-params query-param])]
        (if-let [[f data] (approve get-approval token)]
          (f data req)
          (status (response "") 404))
        (status (response "") 400))
      (handler req))))
