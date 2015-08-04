package com.connehealth.dao;

import com.connehealth.entities.Podcast;
import org.springframework.stereotype.Component;

import java.util.List;

public interface PodcastDao {

    public List<Podcast> getPodcasts();

    /**
     * Returns a podcast given its id
     *
     * @param id
     * @return
     */
    public Podcast getPodcastById(Long id);

    public Long deletePodcastById(Long id);

    public Long createPodcast(Podcast podcast);

    public int updatePodcast(Podcast podcast);

    /** removes all podcasts */
    public void deletePodcasts();

}