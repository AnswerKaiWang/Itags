package com.lytips.ITags.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lytips.ITags.entity.News;
import com.lytips.ITags.model.MessageModel;
import com.lytips.ITags.repository.NewsRepo;

@Service
public class NewsBiz {
	@Autowired NewsRepo newsRepo;
	
	public MessageModel insertBatch(List<News> list) {
		newsRepo.insertBatch(list);
		return new MessageModel();
	}
}
