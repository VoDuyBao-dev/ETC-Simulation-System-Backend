package com.example.ETCSystem.mapper;

import com.example.ETCSystem.dto.common.TopupDTO;
import com.example.ETCSystem.dto.common.WalletDTO;
import com.example.ETCSystem.entities.Topup;
import com.example.ETCSystem.entities.Wallet;
import org.mapstruct.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

@Mapper(componentModel = "spring")
public interface TopupMapper{
    TopupDTO toTopupDTO(Topup topup);
}
