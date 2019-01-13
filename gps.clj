(def state  #{:son-at-home :car-needs-battery :have-money :have-phone-book})

(defn GPS [goals] (if (every? achieve goals) 'solved))

(defn achieve [goal]
  (or 
   (contains? state goal)
      (some apply-op (filter #(appropriate-p goal %) *ops*))
  )
)

(defn appropriate-p [goal op] (contains? (op :add-list) goal))

(defn apply-op [op] 
  (when (every? achieve (op :preconds)) 
    (println (concat "Executing" (str (op :action))))
    (def state (clojure.set/difference state ( op :del-list)))
    (def state (clojure.set/union state (op :add-list))) ;adding
    't
    )
 )

(defstruct op  :action :preconds :add-list :del-list)

(defn make-op [action precond add-list del-list]
  (struct op action precond add-list del-list)
)
 
(def *ops*  (list 
   (make-op :drive-son-to-school #{:son-at-home :car-works} #{:son-at-school} #{:son-at-home})
   (make-op :shop-installs-battery #{:car-needs-battery :shop-knows-problem :shop-has-money} #{:car-works} #{})
   (make-op :tell-shop-problem #{:in-communication-with-shop} #{:shop-knows-problem} #{})
   (make-op :telephone-shop #{:know-phone-number} #{:in-communication-with-shop} #{})
   (make-op :lookup-number #{:have-phone-book} #{:know-phone-number} #{})
   (make-op :give-shop-money #{:have-money} #{:shop-has-money} #{:have-money})
))
