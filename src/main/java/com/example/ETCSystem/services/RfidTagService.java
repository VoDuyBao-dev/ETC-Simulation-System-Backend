package com.example.ETCSystem.services;

import com.example.ETCSystem.entities.RfidTag;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.exceptions.ErrorCode;
import com.example.ETCSystem.repositories.RfidTagRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RfidTagService {

    RfidTagRepository rfidTagRepository;

    public RfidTag getRfidTag(String tagUid) {
        RfidTag rfidTag = rfidTagRepository.findByTagUid(tagUid).orElseThrow(()-> new AppException(ErrorCode.RFID_TAG_NOT_EXISTED));
        return rfidTag;
    }

    public void updateLastPassage(RfidTag rfidTag) {
        rfidTagRepository.save(rfidTag);
    }
}
