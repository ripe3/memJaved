# MemJaved
Basic MemCached library using Java

### Requeriments

[memcached](https://memcached.org/) - v1.4.25 or above

### Usage

    import MemJaved.MemJaved;

    String host = "localhost";
    int port = 11211;

    MemJaved mj = new MemJaved(host, port);

  #### Get value

    boolean result = mj.get("key");
    
  #### Set or update value

    String result = mj.set("key", int_expire_in_seconds, "data");
    
  #### Delete value

    boolean result = mj.delete("key");
