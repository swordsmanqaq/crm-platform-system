package com.heng.mkt.dto;/**
 * @author shkstart
 * @create 2023-03-15 15:24
 */

import com.heng.mkt.domain.Clue;
import com.heng.prod.domain.Product;
import lombok.Data;

import java.util.List;

/**
 *@Auther:Jarvis
 *@Date:2023年03月2023/3/15日15:24
 *@Description:
 */
@Data
public class ClueBusinessDto {
    private Clue clue;
    private Product product;
}
