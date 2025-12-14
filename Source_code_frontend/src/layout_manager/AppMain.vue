<template>

    <router-view v-slot="{ Component }">
        <transition name="fade-transform" mode="out-in">

            <component :is="Component"/>

        </transition>
    </router-view>
</template>


