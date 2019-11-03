package com.gameserver.geodata;

import com.gameserver.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferUShort;
import java.io.File;
import java.io.IOException;

public class GeoEngine {

    private static final Logger log = LoggerFactory.getLogger(GeoEngine.class);

    private static GeoEngine _instance;

    public static GeoEngine getInstance()
    {
        if(_instance == null)
            _instance = new GeoEngine();

        return _instance;
    }

    //�������� landscape �� Unreal
    private final int world_map_x_start = Config.WORLD_MAP_X_START; //������������� �� ���� world composition
    private final int world_map_y_start = Config.WORLD_MAP_Y_START; //������������� �� ���� world composition
    private final float world_map_x_scale = Config.WORLD_MAP_X_SCALE;
    private final float world_map_y_scale = Config.WORLD_MAP_Y_SCALE;
    private final float world_map_z_scale = Config.WORLD_MAP_Z_SCALE;
    //�� ������������� ���� �� 0
    private final float world_map_z_position = Config.WORLD_MAP_Z_POSITION;

    //��������� 2 ��� �� ���������� :(
    private final float world_map_z_min = -256*world_map_z_scale + world_map_z_position;

    //����� ������������� ��������� ��� �������� ����������� heightmap.
    //���� ��������� ���. � Modes ������� Landscape -> Sculpt -> ������ ������ ������� �� Heightmap � Layers � Export to file (������� .png)
    private final int heightmap_image_width;

    //����� ����� �������� ������ � �������� (����� ��� ������� Z ���������)
    private final short[] _heightMap;

    private GeoEngine()
    {
        log.info("Building GeoData");
        BufferedImage image = null;
        //��������� �������� heightmap
        try {
            image = ImageIO.read(new File(System.getProperty("user.dir") + Config.HEIGHTMAP_PATH));
        } catch (IOException e) {
            log.error("Error on trying to load heightmap file");
            e.printStackTrace();
            System.exit(1);
        }

        //����������� �������
        heightmap_image_width = image.getWidth();

        //����������� ������ �� �������� � ������
        DataBufferUShort buffer = (DataBufferUShort) image.getRaster().getDataBuffer();
        _heightMap = buffer.getData();
        log.info("Geodata builded! Loaded {} points", _heightMap.length);
    }

    public float getNearestZ(float x, float y)
    {
        //����������� ���������� � ���� � ���������� �������� heightmap
        int geoX = Math.round((x - world_map_x_start)/world_map_x_scale);
        int geoY = Math.round((y - world_map_y_start)/world_map_y_scale);

        //heightValue - �� 0 �� 65535
        int heightValue = _heightMap[geoX + geoY * heightmap_image_width] & 0xffff;

        //32768 - ��� Z=0
        //��������� 2 ��� �� ���������� :(
        return 256 * world_map_z_scale / 32768f * heightValue + world_map_z_min + world_map_z_position;
    }

}
