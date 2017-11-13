package com.example.q.pocketmusic.data.db;

import android.content.Context;
import android.os.Environment;


import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.data.bean.local.Img;
import com.example.q.pocketmusic.data.bean.local.LocalSong;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.util.SortUtil;
import com.example.q.pocketmusic.util.common.FileUtils;
import com.example.q.pocketmusic.util.common.ToastUtil;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List; /**
 * Created by 鹏君 on 2016/8/31.
 */

/**
 * 本地乐谱Dao
 */
public class LocalSongDao {
    private Dao<LocalSong, Integer> localSongOpe;//操作对象
    private DatabaseHelper helper;
    private Context context;

    public LocalSongDao(Context context) {
        this.context=context.getApplicationContext();
        try {
            helper = DatabaseHelper.getHelper(context.getApplicationContext());
            localSongOpe = helper.getDao(LocalSong.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Context getContext() {
        return context;
    }

    //添加一个localSong,不重复添加
    public boolean add(LocalSong localSong) {
        try {
            List<LocalSong> list = queryForAll();
            if (list != null) {
                for (LocalSong song : list) {
                    if (song.getName().equals(localSong.getName())) {
                        return false;
                    }
                }
            }
            localSongOpe.create(localSong);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void update(LocalSong localSong) {
        try {
            localSongOpe.update(localSong);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //删除一个localSong
    public void delete(LocalSong localSong) {
        try {
            localSongOpe.delete(localSong);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //这里需要改成按照按照文件搜索，并且每个文件只显示文件名，点开之后进入SongActivity界面，可打开大图
    public List<LocalSong> queryForAll() {
        try {
            return localSongOpe.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }

    public LocalSong findBySongId(int id) {
        try {
            return localSongOpe.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LocalSong findByName(String name) {
        try {
            List<LocalSong> list = localSongOpe.queryForEq("name", name);
            if (list == null || list.size() == 0) {
                return null;
            }
            return list.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //检测某个名字是否存在
    public boolean isExist(String name) {
        LocalSong queryResult = findByName(name);
        return queryResult != null;
    }

    //清除LocalSong和Img以及Img文件
    public void deleteLocalRelation(Context context, LocalSong localSong) {
        //从数据库删除
        LocalSongDao localSongDao = new LocalSongDao(context);
        ImgDao imgDao = new ImgDao(context);
        localSongDao.delete(localSong);
        ForeignCollection<Img> imgs = localSong.getImgs();
        CloseableIterator<Img> iterator = imgs.closeableIterator();
        String parent = null;
        try {
            while (iterator.hasNext()) {
                Img img = iterator.next();
                imgDao.delete(img);
                //删除文件
                File file = new File(img.getUrl());
                if (parent == null) {
                    parent = file.getParent();
                }

            }
            if (parent != null) {
                FileUtils.deleteFile(new File(parent));
            }
        } finally {
            try {
                iterator.close();
            } catch (android.database.SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    //得到本地所有图片Pat
    public List<String> getLocalImgsPath(Context context, LocalSong tempLocalSong) {
        LocalSongDao localSongDao = new LocalSongDao(context);
        List<String> imgUrls = new ArrayList<>();
        LocalSong localSong = localSongDao.findBySongId(tempLocalSong.getId());
        if (localSong == null) {
            ToastUtil.showToast("曲谱消失在了异次元。");
            return new ArrayList<>();
        }
        ForeignCollection<Img> imgs = localSong.getImgs();
        CloseableIterator<Img> iterator = imgs.closeableIterator();
        try {
            while (iterator.hasNext()) {
                Img img = iterator.next();
                imgUrls.add(img.getUrl());
            }
        } finally {
            try {
                iterator.close();
            } catch (android.database.SQLException | IOException e) {
                e.printStackTrace();
            }
        }
        return imgUrls;
    }


    //移动，并加入数据库
    public void saveLocalSong(String name, List<String> list) {
        //建立目标文件
         String FILE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Constant.FILE_NAME + "/";
        File dir = new File(FILE_DIR + name);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        LocalSong localSong = new LocalSong();
        localSong.setName(name);
        localSong.setDate(BasePresenter.dateFormat.format(new Date()));
        localSong.setSort(SortUtil.getSort());
        add(localSong);
        ImgDao imgDao=new ImgDao(context);
        for (int i = 0; i < list.size(); i++) {
            File file = new File(list.get(i));//只需要得到名字
            //这里先转移，再加入数据库
            FileUtils.copyFile(list.get(i), dir + "/" + file.getName());// /storage/emulated/0/sina/weibo/.weibo_pic_newthumb.4024457410441510.jpg ---> YuPuDownload/.weibo_pic_newthumb.4024457410441510.jpg;
            Img img = new Img();
            img.setUrl(dir + "/" + file.getName());
            img.setLocalSong(localSong);
            imgDao.add(img);
        }
    }
}
