package com.example.bai2.service;

import com.example.bai2.model.entity.Account;
import com.example.bai2.model.entity.Artwork;
import com.example.bai2.model.entity.Role;
import com.example.bai2.repository.ArtworkRepository;
import com.example.bai2.security.principal.AccountPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArtworkService {
    private final ArtworkRepository artworkRepository;

    public ArtworkService(ArtworkRepository artworkRepository) {
        this.artworkRepository = artworkRepository;
    }

    public List<Artwork> getAllArtworks() {
        // Lấy người dùng đang đăng nhập
        AccountPrincipal accountPrincipal = (AccountPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountPrincipal.getAccount();
        // Lấy ra toàn bộ role của người dùng
        Set<String> roles = account.getRole().stream().map(Role::getRole).collect(Collectors.toSet());
        // Lấy ra toàn bộ artwork
        List<Artwork> artworks = artworkRepository.findAll();
        if (roles.contains("ADMIN")) {
            return artworks;
        } else if (roles.contains("ARTIST")) {
            // Trả về artwork của người dùng hoặc artwork đã được publish
            return artworks.stream().filter(a ->
                    a.getAccount().equals(account) || a.getIsPublished() == true
            ).toList();
        }
        return null;
    }
}
