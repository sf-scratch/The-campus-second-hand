<template>
  <div class="footer">
    <div class="footer-container">
      <div class="footerList">
        <div class="footerItem">
          <h4>XXXX</h4>
        </div>
        <div class="footerItem">
          <h4>XXXX</h4>
        </div>
        <div class="footerItem">
          <h4>XXXX</h4>
        </div>
        <div class="footerItem">
          <h4>XXXX</h4>
        </div>
        <div class="footerItem">
          <h4>XXXX</h4>
        </div>
        <div class="footerItem">
          <h4>XXXX</h4>
        </div>
      </div>
      <div class="copyright">
        <ul class="helpLink">
          <li>关于我们
            <span class="space"></span>
          </li>
          <li>联系我们
            <span class="space"></span>
          </li>
          <li>友情链接
            <span class="space"></span>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>

</script>

<style scoped lang="less">
.footer {
  width: 100%;
  margin: 0 auto;

  .footer-container {
    width: 1200px;
    margin: 0 auto;
    padding: 0 15px;

    .footerList {
      border-bottom: 1px solid #e4e1e1;
      border-top: 1px solid #e4e1e1;
      overflow: hidden;
      padding: 20px 20px 20px 40px;

      .footerItem {
        width: 16.6666667%;
        float: left;

        h4 {
          font-size: 14px;
        }

        .footerItemCon {
          li {
            line-height: 18px;
          }
        }

        &:last-child img {
          width: 121px;
        }
      }
    }

    .copyright {
      padding: 20px;

      .helpLink {
        text-align: center;
        li {
          display: inline;
          font: 12px/1.3 "Microsoft YaHei",Tahoma, Helvetica, Arial, "\5b8b\4f53", sans-serif; /* 用 ascii 字符表示，使得在任何编码下都无问题 */
          color: #333;
          .space {
            border-left: 1px solid #666;
            width: 1px;
            height: 13px;
            background: #666;
            margin: 8px 10px;
          }
        }
      }

      p {
        margin: 10px 0;
        text-align: center;
      }
    }
  }
}
</style>
