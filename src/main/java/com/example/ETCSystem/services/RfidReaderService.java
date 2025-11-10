package com.example.ETCSystem.services;

import com.example.ETCSystem.dto.request.DeviceRequest;
import com.example.ETCSystem.entities.RfidReader;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.exceptions.ErrorCode;
import com.example.ETCSystem.repositories.RfidReaderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RfidReaderService {
    RfidReaderRepository rfidReaderRepository;

    public RfidReader getRfidReader(String readerCode) {
        return rfidReaderRepository.findByReaderUid(readerCode).orElseThrow(() -> new AppException(ErrorCode.RFID_READER_NOT_EXISTED));

    }
}
