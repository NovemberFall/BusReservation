package com.ra.busBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ra.busBooking.model.FeedBack;

public interface FeedbackRepository extends JpaRepository<FeedBack, Integer>{

}
