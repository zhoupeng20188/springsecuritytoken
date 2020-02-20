# springsecuritytoken
## 分布式认证场景
* 此工程为spring security通过token来访问资源
## 测试结果
* localhost:8082/user 带上header为Authorization为Bearer test123456即可访问
* localhost:8082/content带上header为Authorization返回403，因为需要ROLE_ADMIN才能返回
* localhost:8082/user header带上错误token则返回自定义错误信息