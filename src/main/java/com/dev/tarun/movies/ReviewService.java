package com.dev.tarun.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
	
	@Autowired
	ReviewRepository reviewRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public Review createReview(String reviewBody, String imdbId) {
//		Inserts new review
		Review review = reviewRepository.insert(new Review(reviewBody));
		
		
//		Used to update movies collection with the reviewid
		mongoTemplate.update(Movie.class)
					 .matching(Criteria.where("imdbId").is(imdbId))
					 .apply(new Update().push("reviewIds").value(review))
					 .first();
		
		return review;
	}
}
