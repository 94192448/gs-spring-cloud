package main

import (
    "encoding/json"
    "github.com/gin-gonic/gin"
    "io/ioutil"
    "log"
    "net/http"
)
type Status struct {
    Status    string   `json:"status"`
}
type User struct {
    Id      string  `json:"id"`
    Name    string  `json:"name"`
}
func main() {

    sidecarUrl := "http://localhost:3001"

    gin.SetMode(gin.ReleaseMode)

    r := gin.Default()

    r.GET("/health.json", func(c *gin.Context) {
        c.JSON(http.StatusOK, gin.H{
            "status": "UP",
        })
    })

    r.GET("/test", func(c *gin.Context) {
        c.JSON(http.StatusOK,gin.H{"name":"go-test"})
    })
    r.GET("/test-env", func(c *gin.Context) {
        c.JSON(http.StatusOK,gin.H{"name":"go-test"})
    })

    ///env/type

    //通过spring-cloud-netflix-sidecar集成的zuul proxy远程调用微服务
    r.GET("/test-remote", func(c *gin.Context) {

        resp, err := http.Get(sidecarUrl+"/gs-provider/user")

        if err != nil || resp.StatusCode != http.StatusOK {
            log.Println(err.Error())
            c.Status(http.StatusServiceUnavailable)
            return
        }

        var  users  []User

        defer resp.Body.Close()

        contents, err := ioutil.ReadAll(resp.Body)

        json.Unmarshal(contents, &users)

        c.JSON(http.StatusOK,users)
    })

    log.Print("sidecar-url ---> ",sidecarUrl)

    r.Run(":3000")
}
