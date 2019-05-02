
#### 出现问题一
* 问题明细如下：
    > org.apache.ibatis.binding.BindingException: Parameter 'fileId' not found.
* 解决问题：
    > public void insert(@Param("ta")TAttachment attachment);
    
    > NSERT INTO t_attachment ( file_id ) VALUES ( #{ta.fileId} )