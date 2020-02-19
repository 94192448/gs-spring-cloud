package main

import (
    "github.com/gin-gonic/gin"
    "log"
    "net/http"
)
type Status struct {
    Status    string   `json:"status"`
}
func main() {
    sidecarUri := "http://localhost:3001"
    r := gin.Default()
    r.GET("/health.json", func(c *gin.Context) {
        c.JSON(http.StatusOK, gin.H{
            "status": "UP",
        })
    })
    r.GET("/test", func(c *gin.Context) {
        c.JSON(http.StatusOK,gin.H{"name":"go-test"})
    })

    r.GET("/test-remote", func(c *gin.Context) {

        response, err := http.Get(sidecarUri+"/gs-provider/user")

        if err != nil || response.StatusCode != http.StatusOK {
            c.Status(http.StatusServiceUnavailable)
            return
        }

        c.JSON(http.StatusOK,gin.H{"java",response.Body})
    })
    log.Print(sidecarUri)
    r.Run(":3000")
}
