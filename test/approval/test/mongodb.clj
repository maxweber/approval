(ns approval.test.mongodb
  (:use approval.mongodb
        [lazytest.describe
         :only [describe it given do-it using testing]]
        [lazytest.expect :only [expect]]
        [approval.test.core :only [approval token]]
        [somnium.congomongo :only [mongo!]]))

(mongo! :db :test)

(describe "MongoDB store"
  (given [approval (assoc-in approval [:data :key] :value)]
    (do-it "should can store an approval inside a MongoDB and fetch it again via its token"
      (put-approval approval)
      (let [stored-approval (get-approval token)]
        (expect (= approval stored-approval))))))
