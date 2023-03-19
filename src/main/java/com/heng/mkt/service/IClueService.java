package com.heng.mkt.service;

import com.heng.mkt.domain.Activity;
import com.heng.mkt.domain.Clue;
import com.heng.base.service.IBaseService;
import com.heng.mkt.dto.*;
import com.heng.mkt.query.ClueQuery;
import com.heng.org.domain.Employee;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-14
 */
public interface IClueService extends IBaseService<Clue> {

    void saveActivity(ClueActivityDto dto);

    void saveClueAssign(Clue clue, Employee loginUser);

    void saveClueFollow(Clue clue, Employee loginUser);

    void saveClueBusiness(ClueBusinessDto dto, Employee loginUser);

    void clueScrap(Clue clue, Employee loginUser);

    List<Activity> getActivitys(Long typeId);

    List<ClueDataDTO> getDrawPieChartData();

    List<LineChartDTO> getDrawLineChart();

    void importExcel(MultipartFile file);

    void exportExcel(Map<String, Object> param, HttpServletResponse response);

    void exportExcelByKeyword(String keyword, Map<String, Object> param, HttpServletResponse response);
}
