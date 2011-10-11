(ns approval.mongodb
  (:use somnium.congomongo
        [clojure.set :only [rename-keys]]))

(def collection-name :approval)

(defn get-approval [token]
  (-> (fetch-one collection-name :where {:_id token})
      (rename-keys {:_id :token})
      (update-in [:function] keyword)
      (update-in [:data] read-string)))

(defn put-approval [approval]
  (update! collection-name
           {:_id (:token approval)}
           (rename-keys
            (update-in approval [:data] pr-str)
            {:token :_id})))
