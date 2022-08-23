package mb.mybatis.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import mb.mybatis.mybatisplus.entity.User;
import mb.mybatis.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @Author mabo
 * @Date 2022/8/22 20:30
 */
@SpringBootTest
public class MybatisPlusApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectList() {
        System.out.println("---- selectAll method test ----");
        //UserMapper 中的 selectList() 方法的参数为 MP 内置的条件封装器 Wrapper
        //所以不填写就是无任何条件
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    //添加
    @Test
    public void testInsert(){
        User user = new User();
        user.setName("Helen");
        user.setAge(18);
        user.setEmail("55317332@qq.com");

        int result = userMapper.insert(user);

        System.out.println(result); //影响的行数
        System.out.println(user); //id自动回填
    }

    @Test
    public void testUpdateById(){
        User user = new User();
        user.setId(1L);
        user.setAge(28);
        int result = userMapper.updateById(user);
        System.out.println(result);
    }

    /**
     * 测试 乐观锁插件
     */
    @Test
    public void testOptimisticLocker() {
        //查询
        User user = userMapper.selectById(1L);
        //修改数据
        user.setName("Helen Yao");
        user.setEmail("helen@qq.com");
        //执行更新
        userMapper.updateById(user);
    }


    @Test
    public void testSelectById(){
        User user = userMapper.selectById(1L);
        System.out.println(user);
    }

    @Test
    public void testSelectBatchIds(){
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        users.forEach(System.out::println);
    }

    @Test
    public void testSelectByMap(){
        HashMap<String, Object> map = new HashMap<>();
        // 列名
        // map中的key对应的是数据库中的列名。例如数据库user_id，实体类是userId，这时map的key需要填写user_id
        map.put("name", "Helen");
        map.put("age", 18);
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }


    @Test
    public void testSelectPage() {
        Page<User> page = new Page<>(1,5);
        userMapper.selectPage(page, null);
        page.getRecords().forEach(System.out::println);

        System.out.println(page.getCurrent());//当前第几页
        System.out.println(page.getPages());//总页数
        System.out.println(page.getSize());//每页显示数量
        System.out.println(page.getTotal());//总记录数
        System.out.println(page.hasNext());//是否有下一页
        System.out.println(page.hasPrevious());//是否有上一页

        //控制台sql语句打印：SELECT id,name,age,email,create_time,update_time FROM user LIMIT 0,5
    }

    /**
     * 根据id删除记录
     */
    @Test
    public void testDeleteById(){
        int result = userMapper.deleteById(8L);
        System.out.println(result);
    }

    /**
     * 批量删除
     */
    @Test
    public void testDeleteBatchIds() {
        int result = userMapper.deleteBatchIds(Arrays.asList(8, 9, 10));
        System.out.println(result);
    }

    /**
     * 简单的条件删除
     */
    @Test
    public void testDeleteByMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "Helen");
        map.put("age", 18);
        int result = userMapper.deleteByMap(map);
        System.out.println(result);
    }

    /**
     * 测试 逻辑删除
     */
    @Test
    public void testLogicDelete() {
        // 注意：被删除数据的deleted 字段的值必须是 0，才能被选取出来执行逻辑删除的操作
        int result = userMapper.deleteById(1L);
        System.out.println(result);
    }


    /**
     * ge、gt、le、lt、isNull、isNotNull（selectList）
     */
    @Test
    public void testSelect() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.le("age", 28);

        List<User> users = userMapper.selectList(queryWrapper);
        //int delete = userMapper.delete(queryWrapper);

        System.out.println(users);
    }

    /**
     * eq、ne（seletOne）
     */
    @Test
    public void testSelectOne() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "Tom");

        // 注意：seletOne返回的是一条实体记录，当出现多条时会报错
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }

    /**
     * between、notBetween
     */
    @Test
    public void testSelectCount() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 包含大小边界
        queryWrapper.between("age", 20, 30);
        Integer count = userMapper.selectCount(queryWrapper);
        System.out.println(count);
    }

    /**
     * like、notLike、likeLeft、likeRight
     */
    @Test
    public void testLike(){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
//        userQueryWrapper.like("name","d");//  %d%
//        userQueryWrapper.likeLeft("name","d"); //%d
        userQueryWrapper.likeRight("name","T"); // d%
        List<User> users = userMapper.selectList(userQueryWrapper);
        System.out.println(users);
    }

    /**
     * orderBy、orderByDesc、orderByAsc
     */
    @Test
    public void testSelectListOrderBy() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * last
     * 直接拼接到 sql 的最后
     */
    @Test
    public void testSelectListLast() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.last("limit 1");
        //queryWrapper.last(" or 1=1");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 指定要查询的列
     */
    @Test
    public void testSelectListColumn() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "name", "age");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
}