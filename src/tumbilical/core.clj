(ns tumbilical.core
  (:require [clj-http.client :as http]
            [environ.core :refer [env]]
            [oauth.client :as oauth]
            [cheshire.core :refer [parse-string]]
            [clj-http.client :as http]))


(def ^:private tumblr-prefix "https://api.tumblr.com/v2/blog/")


(defn info [blogname api-key]
  (->> api-key
       (format "%s%s/info?api_key=%s" tumblr-prefix blogname)
       clj-http.client/get
       :body
       parse-string
       clojure.walk/keywordize-keys))


;; (info "johnjacobsenart.tumblr.com" (:tumblr-api-key env))

;; ;;=>
;; {:meta {:status 200, :msg "OK"},
;;  :response
;;  {:blog
;;   {:description
;;    "Paintings, drawings, and various musings by John Jacobsen. 100%  GMO-free or your money back.",
;;    :updated 1438570197,
;;    :name "johnjacobsenart",
;;    :is_nsfw false,
;;    :ask_page_title "Ask me anything",
;;    :title "John Jacobsen",
;;    :ask_anon false,
;;    :share_likes false,
;;    :url "http://johnjacobsenart.tumblr.com/",
;;    :posts 32,
;;    :ask false}}}

