package vip.justlive.frost.executor.example;

import java.util.List;
import org.springframework.stereotype.Component;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import vip.justlive.frost.api.model.JobSharding;
import vip.justlive.frost.core.job.IJob;
import vip.justlive.frost.core.job.Job;
import vip.justlive.frost.core.job.JobContext;

@Slf4j
@Job(value = "shardingJob", desc = "分片job例子")
@Component
public class ShardingJob implements IJob {

  @Override
  public void execute(JobContext ctx) {

    int max = 10;
    JobSharding sharding = ctx.getSharding();
    String msg = "未选择分片任务";
    if (sharding != null) {
      msg = String.format("[%s of %s]", sharding.getIndex(), sharding.getTotal());
    }
    List<Integer> list = Lists.newArrayList();
    for (int i = 0; i < max; i++) {
      if (sharding != null) {
        if (i % sharding.getTotal() == sharding.getIndex()) {
          list.add(i);
        }
      } else {
        list.add(i);
      }
    }
    log.info("{},执行结果：{}", msg, list);
  }

}
