package com.mi.nanqiao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mi.nanqiao.dao.PhysioProDao;
import com.mi.nanqiao.dto.PhysioDTO;
import com.mi.nanqiao.entity.Physio;
import com.mi.nanqiao.dao.PhysioDao;
import com.mi.nanqiao.entity.PhysioPro;
import com.mi.nanqiao.service.PhysioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 理疗师表(Physio)表服务实现类
 *
 * @author makejava
 * @since 2023-01-25 21:45:13
 */
@Slf4j
@Service("physioService")
@RequiredArgsConstructor
public class PhysioServiceImpl implements PhysioService {

    private final PhysioDao physioDao;

    private final PhysioProDao physioProDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public PhysioDTO queryById(Integer id) {
        Physio physio = physioDao.queryById(id);
        List<Integer> projectIds = physioProDao.findProjectIdsByPhysioId(id);
        PhysioDTO physioDTO = new PhysioDTO();
        BeanUtils.copyProperties(physio,physioDTO);
        physioDTO.setProjectIds(projectIds);
        return physioDTO;
    }

    /**
     * 分页查询
     *
     * @param page 分多少页
     * @param size 每页多少条数据
     * @param nickname 昵称
     * @return 查询结果
     */
    @Override
    public PageInfo<Physio> queryByPage(Integer page,Integer size,String nickname) {
        PageHelper.startPage(page,size);
        List<Physio> list = physioDao.queryAllByLimit(nickname);
        return new PageInfo<Physio>(list);
    }

    /**
     * 新增数据
     *
     * @param physioDTO 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public Physio insert(PhysioDTO physioDTO){
        Physio physio = new Physio();
        physio.setLike(0);
        physio.setState(1);
        physio.setBillCount(0);
        physio.setAvatar(physioDTO.getAvatar());
        physio.setNickname(physioDTO.getNickname());
        this.physioDao.insert(physio);
        Integer id = physio.getId();
        List<Integer> projectIds = physioDTO.getProjectIds();

        if (batchInsertPhysioProject(id, projectIds)) {
            return physio;
        }
        return null;
    }


    private boolean batchInsertPhysioProject(Integer id, List<Integer> projectIds) {
        ArrayList<PhysioPro> physios = new ArrayList<>();
        projectIds.forEach(proId ->{
            PhysioPro physioPro = new PhysioPro();
            physioPro.setPhysioId(id);
            physioPro.setProjectId(proId);
            physios.add(physioPro);
        });
        return physioProDao.insertBatch(physios) >0;

    }

    /**
     * 修改数据
     *
     * @param physioDTO 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public PhysioDTO update(PhysioDTO physioDTO) {
        Physio physio = new Physio();
        Integer id = physioDTO.getId();
        physio.setId(id);
        physio.setAvatar(physioDTO.getAvatar());
        physio.setNickname(physioDTO.getNickname());
        physio.setState(physioDTO.getState());
        if (physioDao.update(physio)>0) {
            if (physioProDao.deleteByPhysioId(id)>0){
                if (batchInsertPhysioProject(id,physioDTO.getProjectIds())) {
                    return this.queryById(id);
                }
            }
        }
        return null;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    @Transactional
    public boolean deleteById(Integer id) {
        if ((physioDao.deleteById(id) >0 )) {
            physioProDao.deleteByPhysioId(id);
            return true;
        }
        return false;
    }

    /**
     * 根据项目id查询关联的理疗师
     *
     * @param projectId 项目id
     * @return 理疗师集合
     */
    @Override
    public List<Physio> queryByProject(Integer projectId) {
        return physioDao.queryByProject(projectId);
    }

    @Override
    public long count() {
        return physioDao.count(new Physio());
    }
}
