<template>
    <div class="header">
        <div class="logo-nav-container">
        <div class="logo-container">
        <img id="logo" src="/logo_thumbnail.png">
        <h2 class="tagline">ALE TRAIL</h2>
        </div>

        <div id="nav" >
            
            <router-link id="headerHome" v-bind:to="{ name: 'home' }">Home</router-link> &nbsp;&nbsp;|&nbsp;
            <button id="random" @click="handleClick">Beer Me</button> &nbsp;
            <input id="box" type="text" v-model="randomBrewery" readonly /> &nbsp;&nbsp;|&nbsp;
            <router-link id="brew" to="/breweries">View All Breweries</router-link> &nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
            <router-link id="logoutHeader" v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link> &nbsp;&nbsp;&nbsp;
            
            
        </div>
        </div>
        <div style="height: 30px; width: 100%; background-color:  rgba(6,40,81,255); color: white;"/>
        
    
    </div>
    
</template>

<script> 
import axios from 'axios';

export default {
    name: 'the-header',
    data() {
        return {
            randomBrewery: '',
        };
    },
    methods: {
        async handleClick() {
        
            try {
                const response = await axios.get('/breweries/random');
                this.randomBrewery = response.data.name;
            } catch (error) {
                console.error("Error getting brewery: ", error);
            }
        },
    },
};
</script>

<style>

.logo-nav-container {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.logo-container{
    display: flex;
    align-items: center;
}

#box{
    width: 220px;
}

#random {
border: 1px solid black;
background: rgba(6,40,81,255);
background-clip: padding-box;
color: white;
padding: 4px 20px;
border-radius: 5px;
}


#logo,
.tagline {
display: inline-block;
vertical-align: middle;
color: #062851;
font-size: 30px;
font-weight: bolder;
}

.tagline {
    margin-left: 10px;
    
}

#nav {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
    align-items: center;
    
}

#logoutHeader {
border: 1px solid black;
background: rgba(6,40,81,255);
background-clip: padding-box;
color: white;
padding: 2px 20px;
border-radius: 5px;
}



#headerHome {
border: 1px solid black;
background: rgba(6,40,81,255);
background-clip: padding-box;
color: white;
padding: 2px 20px;
border-radius: 5px;
}


#brew {
border: 1px solid black;
background: rgba(6,40,81,255);
background-clip: padding-box;
color: white;
padding: 2px 20px;
border-radius: 5px;
}

@media screen and (max-width: 900px) {
    
   .logo-nav-container{
       flex-direction: column;
   }

   .tagline{
       font-size: 24px;
       margin-left: 5px;
       margin-top: 5px;
   }

    #box {
        width: 220px;
    }

    #nav {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
    align-items: center;
    
}

}
    


</style>