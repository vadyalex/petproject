(ns petproject.handler-test

  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [petproject.handler :refer :all]))

(deftest test-app
  (testing "/myresource route"
    (let [response (app (mock/request :get "/myresource"))]
      (is (= (:status response) 200))
      (is (= (:body response) "{\"message\":\"Hello Word\"}"))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/unknown"))]
      (is (= (:status response) 404))
      (is (= (:body response) "Not Found"))))

  (testing "/some/test route"
    (let [response (app (mock/request :get "/some/test"))]
      (is (= (:status response) 200))
      (is (= (:body response) "THE ROOT /some/test"))))

  (testing "/some/test/here route"
    (let [response (app (mock/request :get "/some/test/here"))]
      (is (= (:status response) 200))
      (is (= (:body response) "THE /some/test/here"))))

  (testing "/some/test/there route"
    (let [response (app (mock/request :get "/some/test/there"))]
      (is (= (:status response) 200))
      (is (= (:body response) "THE /some/test/there"))))

  )
