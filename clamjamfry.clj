(def massive (read-seq-from-file "massive.clj"))

;; Top-level forms

(count massive)

;; Total amount of forms

(count (tree-seq seq? seq massive))

;; Type of top-level form

(take 
	100 
	(sort-by val > (frequencies (map #(if (seq? %) (first %) %) massive))))

;; Count of docstrings – third element should be a string

(sort-by val > 
	(frequencies 
		(map 
			#(type (nth % 2)) 
			(filter #(and (seq? %) (= 'defn (first %))) massive))))

;; Same for private functions 

(sort-by val > 
	(frequencies 
		(map 
			#(type (nth % 2)) 
			(filter #(and (seq? %) (= 'defn- (first %))) massive))))

;; Length of docstrings distribution

(sort-by 
	val 
	> 
	(frequencies 
		(map 
			#(count (nth % 2)) 
			(filter 
				#(and 
					(seq? %) 
					(or (= 'defn (first %)) (= 'defn- (first %))) 
					(= (type (nth % 2)) java.lang.String)) 
				massive))))

;; Distribution of argument counts by function

(frequencies 
	(map count 
		 (filter identity 
				 (map 
				 	#(first (filter vector? %)) 
				 	(filter 
				 		#(and 
				 			(seq? %) 
				 			(or (= 'defn (first %)) (= 'defn- (first %)))) 
				 		massive)))))

;; Count meaningful internal forms

(count (filter seq? (tree-seq seq? seq massive)))

;; Count first elements of meaningful forms

(take 
	200 
	(sort-by 
		val 
		> 
		(frequencies (map first (filter seq? (tree-seq seq? seq massive))))))
