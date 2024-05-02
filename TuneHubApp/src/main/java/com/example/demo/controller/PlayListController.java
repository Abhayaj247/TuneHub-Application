package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entities.PlayList;
import com.example.demo.entities.Songs;
import com.example.demo.services.PlayListService;
import com.example.demo.services.SongsService;
@Controller
public class PlayListController {
	@Autowired
	PlayListService pserv;
	
	@Autowired
	SongsService sserv;
	
	@GetMapping("/createplaylist")
	public String createPlayList(Model model) {
		List<Songs> songsList = sserv.fetchAllSongs();
		model.addAttribute("songsList", songsList);
		return "createplaylist";
	}
	@PostMapping("/addplaylist")
	public String addPlayList(@ModelAttribute PlayList playlist) {
		//adding playlist
		pserv.addPlaylist(playlist);
		//update song table
		List<Songs> songsList = playlist.getSongs();
		for(Songs song : songsList) {
			song.getPlayList().add(playlist);
			sserv.updateSong(song);
		}
		return "playlistsuccess";
	}
	@GetMapping("/viewplaylist")
	public String viewPlaylist(Model model) {
		List<PlayList> plist = pserv.fetchPlaylist();
		model.addAttribute("plist", plist);
		System.out.println(plist);
		return "viewplaylist";
	}
}
