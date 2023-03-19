package com.heng.mkt.mapper;

import com.heng.mkt.domain.Activity;
import com.heng.mkt.domain.Clue;
import com.heng.base.mapper.BaseMapper;
import com.heng.mkt.dto.ClueDataDTO;
import com.heng.mkt.dto.ImportDTO;
import com.heng.mkt.dto.LineChartDTO;
import com.heng.mkt.query.ClueQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-14
 */
public interface ClueMapper extends BaseMapper<Clue> {


    void deleteActivityByClueId(Long clueId);

    void saveActivity(@Param("clueId") Long clueId, @Param("activityId") List<Long> activityId);

    List<Activity> getActivitys(@Param("typeId") Long typeId);

    List<ClueDataDTO> getDrawPieChartData();

    List<LineChartDTO> getDrawLineChart();

    void batchImport(@Param("resultList") List<ImportDTO> resultList);

    //获取所有的线索数据
    List<Clue> getClueForEmport();


    List<Clue> getClueForEmportByKeyword(String keyword);
}
