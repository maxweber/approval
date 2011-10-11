(ns approval.test.ring-middleware
  (:use approval.ring-middleware
        approval.core
        [lazytest.describe
         :only [describe it given do-it using testing]]
        [lazytest.expect :only [expect]]
        ring.mock.request
        [approval.test.core :only [token stub-get-approval data]]
        [ring.middleware.params :only [wrap-params]])
  (:require [ring.util.response :as res]))

(def function-key :confirm-email-address)

(def approval (create-approval token function-key data))

(def done-nothing "done nothing")

(defn stub-handler [req]
  (res/response done-nothing))

(def prefix "/approve")

(def query-param "token")

(def ring-request
     (request :get prefix {query-param token}))

(def get-approval
     (stub-get-approval approval))

(def app
     (-> stub-handler
         (wrap-approval get-approval prefix query-param)
         wrap-params))

(def success "confirmation successful")

(defn response-body [data]
  (str success " " (:user-id data)))

(defmethod approved-function function-key [_]
           (fn [data request]
             (res/response (response-body data))))

(describe "Approval middleware"
  (it "should get the approval for the token in the query parameters and use the approved function to compute the response for the request"
    (= (response-body data) (:body (app ring-request)))))
