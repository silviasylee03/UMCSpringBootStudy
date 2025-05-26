package umc.spring.converter;

import org.springframework.data.domain.Page;
import umc.spring.domain.Mission;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.web.dto.MissionChallengeResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class MissionConverter {
    public static MissionChallengeResponseDTO.StoreMissionDTO missionToStoreMissionDTO(Mission mission) {
        return MissionChallengeResponseDTO.StoreMissionDTO.builder()
                .reward(mission.getReward())
                .content(mission.getMissionSpec())
                .missionStatus(mission.getStatus().toString())
                .deadline(mission.getDeadline())
                .build();
    }

    public static MissionChallengeResponseDTO.StoreMissionListDTO storeMissionListDTO(Page<Mission> missionList) {
        List<MissionChallengeResponseDTO.StoreMissionDTO> missionDTOList = missionList.stream()
                .map(MissionConverter::missionToStoreMissionDTO).collect(Collectors.toList());

        return MissionChallengeResponseDTO.StoreMissionListDTO.builder()
                .isLast(missionList.isLast())
                .isFirst(missionList.isFirst())
                .totalPage(missionList.getTotalPages())
                .totalElements(missionList.getTotalElements())
                .listSize(missionDTOList.size())
                .storeMissions(missionDTOList)
                .build();
    }

    public static MissionChallengeResponseDTO.MemberChallengingMissionDTO missionToMemberChallengingMissionDTO(MemberMission mission) {
        return MissionChallengeResponseDTO.MemberChallengingMissionDTO.builder()
                .reward(mission.getMission().getReward())
                .content(mission.getMission().getMissionSpec())
                .missionStatus(mission.getStatus().toString())
                .deadline(mission.getMission().getDeadline())
                .storeName(mission.getMission().getStore().getName())
                .build();
    }

    public static MissionChallengeResponseDTO.MemberChallengingMissionListDTO missionToMemberChallengingMissionListDTO(Page<MemberMission> missionList) {
        List<MissionChallengeResponseDTO.MemberChallengingMissionDTO> missionDTOList = missionList.stream()
                .map(MissionConverter::missionToMemberChallengingMissionDTO).collect(Collectors.toList());

        return MissionChallengeResponseDTO.MemberChallengingMissionListDTO.builder()
                .isLast(missionList.isLast())
                .isFirst(missionList.isFirst())
                .totalPage(missionList.getTotalPages())
                .totalElements(missionList.getTotalElements())
                .listSize(missionDTOList.size())
                .memberChallengingMissions(missionDTOList)
                .build();
    }
}