(ns tumbilical.core
  (:require [clj-http.client :as http]
            [environ.core :refer [env]]
            [oauth.client :as oauth]
            [cheshire.core :refer [parse-string]]
            [clj-http.client :as http]))


(def ^:private tumblr-prefix "https://api.tumblr.com/v2/blog/")


(defn avatar
  "
  (count (avatar \"johnjacobsenart.tumblr.com\" 16))
  ;;=>
  3183
  (count (avatar \"johnjacobsenart.tumblr.com\" 512))
  ;;=>
  34668
  (count (avatar \"johnjacobsenart.tumblr.com\"))
  ;;=>
  4331
  "
  [blogname & [size]]
  {:pre [(or (nil? size) (#{16 24 30 40 48 64 96 128 512} size))]}
  (->> (format "%s%s/avatar%s"
               tumblr-prefix
               blogname
               (if size (str "/" size) ""))
       clj-http.client/get
       :body))


(defn info
  "
  (info \"johnjacobsenart.tumblr.com\" (:tumblr-api-key env))
  ;;=>
  {:meta {:status 200, :msg \"OK\"},
   :response
   {:blog
    {:description
     \"Paintings, drawings, and various musings by John Jacobsen....\",
     :updated 1438570197,
     :name \"johnjacobsenart\",
     :is_nsfw false,
     :ask_page_title \"Ask me anything\",
     :title \"John Jacobsen\",
     :ask_anon false,
     :share_likes false,
     :url \"http://johnjacobsenart.tumblr.com/\",
     :posts 32,
     :ask false}}}
  "
  [blogname api-key]
  (->> api-key
       (format "%s%s/info?api_key=%s" tumblr-prefix blogname)
       clj-http.client/get
       :body
       parse-string
       clojure.walk/keywordize-keys))
