<template>
	<div class="user">
		<div class="user-img">
      <img :height="avatarSize" :src="avatar?avatar:require('./img/icon/avatar.jpg')"/>
    </div>
	</div>
</template>


<script>
	export default {
	  props:{
	  	avatar:{
	  		type:String,
	  		default:''
	  	},
      avatarSize:{
        type:Number,
        default:45
      },
	  },
	  data() {
	    return {
	      comment: ''
	    }
	  },

	}
</script>

<style type="text/css" scoped>
	.user{
		height: 50px;
		width: 50px;
	}
	.user-img img{
		border-radius:50%;
	}

</style>
