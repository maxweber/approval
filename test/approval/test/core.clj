(ns approval.test.core
  (:use approval.core
        [lazytest.describe
         :only [describe it given do-it using testing]]
        [lazytest.expect :only [expect]]
        [ring.middleware.params :only [wrap-params]]))

(def function-key :reset-password)

(def data
     {:user-id "123"})

(def token (generate-token))

(def approval (create-approval token function-key data))

(defn stub-get-approval [approval]
  (fn [the-token] approval))

(describe "An approval"
  (it "should consist of an authentication token, a keyword which identifies the approved function call and the parameter data for the function call"
    (= approval
       {:token token
        :function function-key
        :data data})))

(def an-approved-function
     (fn [data] data))

(defmethod approved-function function-key [_]
          an-approved-function)

(describe "Approve"
  (given [get-approval (stub-get-approval approval)
          result (approve get-approval token)]
    (it "should return the approved function and approval data if the token was found"
      (= result [an-approved-function data]))))

