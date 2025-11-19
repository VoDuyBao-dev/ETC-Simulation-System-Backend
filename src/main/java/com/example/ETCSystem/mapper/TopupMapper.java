package com.example.ETCSystem.mapper;

import com.example.ETCSystem.dto.common.TopupDTO;
import com.example.ETCSystem.dto.common.WalletDTO;
import com.example.ETCSystem.dto.response.UserResponse;
import com.example.ETCSystem.entities.Topup;
import com.example.ETCSystem.entities.User;
import com.example.ETCSystem.entities.Wallet;
import org.mapstruct.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TopupMapper{
    TopupDTO toTopupDTO(Topup topup);
    List<TopupDTO> toTopupDTOList(List<Topup> topups);
}
