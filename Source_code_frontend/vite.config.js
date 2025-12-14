import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'
import {defineConfig} from 'vite'
import AutoImport from "unplugin-auto-import/vite" 
import Components from 'unplugin-vue-components/vite' 
import VueSetupExtend from 'vite-plugin-vue-setup-extend'  


const baseApi = 'http://127.0.0.1:8083/product'   
const outputDir = 'build_out'
import * as path from 'path';
const titleName = 'EchoVerse' 

export default ({mode, command}) => {

    return defineConfig({

        base: command === 'serve' ? './' : '/',
        define: {
            'process.platform': null,
            'process.version': null,
            'process.env': {
                VUE_APP_BASE_API: baseApi,
                title: titleName
            }
        },
        clearScreen: true,
      
        server: {
            hmr: {
                overlay: false
            }, 
            port: 8889, 
            open: true, 
            cors: false 

        },
        plugins: [
            vue(),
            vueJsx(),

            AutoImport({
                imports: [
                    "vue",
                    "vue-router",
                    {
                        'vue': [
                            'defineProps',
                            'defineEmits',
                            'defineExpose',
                            'withDefaults'
                        ]
                    },

                ], 
                dts: "src/auto-import.d.ts" 
            }),
            Components({
                dirs: ['src/components'], 
                include: [/\.vue$/, /\.vue\?vue/, /\.jsx$/],
                dts: "src/components.d.ts" 

            }),
            VueSetupExtend(),
            
            {
                name: 'dev-auto-import-element-plus',
                transform(code, id) {
                    if (command === 'serve' && /src\/main.js$/.test(id)) {
                        return {
                            code: code.replace('/* importElementPlusPlaceholder */', `
                                import ElementPlus from 'element-plus';
                                import 'element-plus/dist/index.css';
                                app.use(ElementPlus);
                            `),
                            map: null
                        }
                    }
                }
            }
        ],
        
        build: {
            outDir: outputDir,
            emptyOutDir: true,
            minify: 'terser',
            brotliSize: false,
            
            chunkSizeWarningLimit: 2000,
            terserOptions: {
                compress: {
                    
                    drop_console: false,
                    drop_debugger: false
                }
            },
            assetsDir: 'static',
            rollupOptions: {
                
                output: {
                    chunkFileNames: 'static/js/[name]-[hash].js',
                    entryFileNames: 'static/js/[name]-[hash].js',
                    assetFileNames: 'static/[ext]/[name]-[hash].[ext]'
                }
            }
        },
        optimizeDeps: {
            include: [
                'element-plus/dist/locale/zh-cn.mjs',
                "@vueuse/core",

            ]
        },
        resolve: {
            alias: {
                '~': path.resolve(__dirname, './'),
                '@': path.resolve(__dirname, 'src')
            },
            extensions: ['.js', '.ts', '.jsx', '.tsx', '.json', '.vue', '.mjs']
        },

    })
}
