package com.example.ETCSystem.services;

import com.example.ETCSystem.dto.request.DeviceRequest;
import com.example.ETCSystem.entities.RfidReader;
import com.example.ETCSystem.entities.TagRead;
import com.example.ETCSystem.enums.ProcessResult;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.exceptions.ErrorCode;
import com.example.ETCSystem.repositories.TagReadRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TagReadService {
    TagReadRepository tagReadRepository;
    RfidReaderService rfidReaderService;

    public TagRead saveTagRead(DeviceRequest deviceRequest) {
        RfidReader rfidReader = rfidReaderService.getRfidReader(deviceRequest.getReaderUid());

        TagRead tagRead = TagRead.builder()
                .reader(rfidReader)
                .tagUid(deviceRequest.getRfidTagCode())
                .processResult(ProcessResult.PENDING)
                .build();

        try{
            return tagReadRepository.save(tagRead);
        }catch(Exception e){
            throw new AppException(ErrorCode.SAVE_TAG_READ_FAILED);
        }

    }

    public void updateTagReadProcessResult(TagRead tagRead, boolean success) {
        tagRead.setProcessed(true);
        tagRead.setProcessResult(success ? ProcessResult.SUCCESS : ProcessResult.FAILED);
        tagReadRepository.save(tagRead);
    }
}
