package la.hackspace.pomodoro;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MenuPomodoro extends AppCompatActivity {

    //region Inicialización de variables de vista
    ImageButton Comenzar,Detener;
    ProgressBar Tiempo;
    Spinner Minutos, Cantidad;
    ArrayAdapter<Integer> aaMinutos, aaCantidad;
    TextView Estado;
    //endregion Inicialización de variables de vista

    //region Inicialización de variables internas
    int TiempoActual, NumeroActual, ContadorActual,AlarmaActual;
    long InicioChr;
    boolean BotonAct,Descanso,Detenido;
    //endregion Inicialización de variables internas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pomodoro);

        //region Vinculación y designación de variables
        Detenido= true;
        BotonAct = false;
        Comenzar = findViewById(R.id.ibComenzar);
        Tiempo = findViewById(R.id.pbDuracion);
        Minutos = findViewById(R.id.spMinutos);
        Cantidad = findViewById(R.id.spPomodoros);
        Estado = findViewById(R.id.tvEstado);
        Detener = findViewById(R.id.ibDetener);
        Descanso = false;
        //endregion Vinculación y designación de variables

        //Designación e ingreso de datos al adaptador de minutos
        Integer[] aMinutos = {20,25,30};
        aaMinutos = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,aMinutos);
        Minutos.setAdapter(aaMinutos);

        //Designación e ingreso de datos al adaptador de cantidad
        Integer[] aCantidad = {1,2,3,4};
        aaCantidad = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,aCantidad);
        Cantidad.setAdapter(aaCantidad);

        //region Recuperación de estado de componentes, datos y alarmas en cambio de vista
        if(savedInstanceState!= null){
            //region Recuperación de estado de componentes
            TiempoActual = savedInstanceState.getInt("Minutos");
            NumeroActual = savedInstanceState.getInt("NumeroActual");
            BotonAct = savedInstanceState.getBoolean("Cronometrado");
            Estado.setText(savedInstanceState.getString("Leyenda"));
            ContadorActual = savedInstanceState.getInt("ContadorActual");
            Descanso = savedInstanceState.getBoolean("Descanso");
            InicioChr = savedInstanceState.getLong("INICIAL");
            Descanso = savedInstanceState.getBoolean("Descanso");
            Detenido = savedInstanceState.getBoolean("DETENIDO");
            //endregion Recuperación de estado de componentes

            //region Actualización de alarma
            if(Descanso){
                AlarmaActual = 3;
            }
            else if(NumeroActual==4){
                AlarmaActual = 2;
            }
            else{
                AlarmaActual = 1;
            }
            //endregion Actualización de alarma

            //region Actualización de botones
            if (BotonAct){
                Comenzar.setVisibility(View.GONE);
                Tiempo.setVisibility(View.VISIBLE);
            }
            //endregion Actualización de botones

            //Verificar si la alarma esta detenida
            if(!Detenido){
                Detener.setVisibility(View.VISIBLE);

                //region Continuar 1° pausa
                if (ContadorActual == 1){

                    //region Pomodoro 2
                    if(NumeroActual >1){
                        if(Descanso){
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if(!Detenido) {
                                        ContadorActual = 2;
                                        Estado.setText("Pomodoro N° : 2 - " + TiempoActual + " minutos");
                                        InicioChr = SystemClock.elapsedRealtime();
                                        Descanso = false;

                                        //region Pomodoro 3
                                        if (NumeroActual > 2) {
                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if(!Detenido) {
                                                        Estado.setText("A descansar - 5 minutos");
                                                        InicioChr = SystemClock.elapsedRealtime();
                                                        Descanso = true;

                                                        Handler handler = new Handler();
                                                        handler.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                if(!Detenido) {
                                                                    ContadorActual = 3;
                                                                    Estado.setText("Pomodoro N° : 3 - " + TiempoActual + " minutos");
                                                                    InicioChr = SystemClock.elapsedRealtime();
                                                                    Descanso = false;

                                                                    //region Pomodoro 4
                                                                    if (NumeroActual > 3) {
                                                                        Handler handler = new Handler();
                                                                        handler.postDelayed(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                if(!Detenido) {
                                                                                    ContadorActual = 4;
                                                                                    Estado.setText("A descansar - 5 minutos");
                                                                                    InicioChr = SystemClock.elapsedRealtime();
                                                                                    Descanso = true;

                                                                                    Handler handler = new Handler();
                                                                                    handler.postDelayed(new Runnable() {
                                                                                        @Override
                                                                                        public void run() {
                                                                                            if(!Detenido) {
                                                                                                ContadorActual = 4;
                                                                                                Estado.setText("Pomodoro N° : 4 - " + TiempoActual + " minutos");
                                                                                                InicioChr = SystemClock.elapsedRealtime();
                                                                                                Descanso = false;

                                                                                                Handler handler = new Handler();
                                                                                                handler.postDelayed(new Runnable() {
                                                                                                    @Override
                                                                                                    public void run() {
                                                                                                        Detener.setVisibility(View.GONE);
                                                                                                        Estado.setText("¡Lo lograste! ahora necesitas descansar - 30 minutos");
                                                                                                        Tiempo.setVisibility(View.GONE);
                                                                                                        Comenzar.setVisibility(View.VISIBLE);
                                                                                                        BotonAct = false;
                                                                                                    }
                                                                                                }, TiempoActual * 60000);
                                                                                            }

                                                                                        }
                                                                                    }, 300000);
                                                                                }
                                                                            }
                                                                        }, TiempoActual * 60000);
                                                                    }
                                                                    //endregion Pomodoro 4

                                                                    //region Final 3
                                                                    else {
                                                                        Handler handler = new Handler();
                                                                        handler.postDelayed(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                Estado.setText("A descansar - 5 minutos");
                                                                                Tiempo.setVisibility(View.GONE);
                                                                                Comenzar.setVisibility(View.VISIBLE);
                                                                                BotonAct = false;
                                                                            }
                                                                        }, TiempoActual * 60000);
                                                                    }
                                                                    //endregion Final 3
                                                                }

                                                            }
                                                        }, 300000);
                                                    }
                                                }
                                            }, TiempoActual * 60000);
                                        }
                                        //endregion Pomodoro 3

                                        //region Final 2
                                        else {
                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Estado.setText("A descansar - 5 minutos");
                                                    Tiempo.setVisibility(View.GONE);
                                                    Comenzar.setVisibility(View.VISIBLE);
                                                    BotonAct = false;
                                                }
                                            }, TiempoActual * 60000);
                                        }
                                        //endregion Final 2
                                    }
                                }
                            }, 300000 - savedInstanceState.getLong("ContinuarChr"));
                        }
                        else {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if(!Detenido) {
                                        Estado.setText("A descansar - 5 minutos");
                                        InicioChr = SystemClock.elapsedRealtime();
                                        Descanso = true;

                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                if(!Detenido) {
                                                    ContadorActual = 2;
                                                    Estado.setText("Pomodoro N° : 2 - " + TiempoActual + " minutos");
                                                    InicioChr = SystemClock.elapsedRealtime();
                                                    Descanso = false;

                                                    //region Pomodoro 3
                                                    if (NumeroActual > 2) {
                                                        Handler handler = new Handler();
                                                        handler.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                if(!Detenido) {
                                                                    Estado.setText("A descansar - 5 minutos");
                                                                    InicioChr = SystemClock.elapsedRealtime();
                                                                    Descanso = true;

                                                                    Handler handler = new Handler();
                                                                    handler.postDelayed(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            if(!Detenido) {
                                                                                ContadorActual = 3;
                                                                                Estado.setText("Pomodoro N° : 3 - " + TiempoActual + " minutos");
                                                                                InicioChr = SystemClock.elapsedRealtime();
                                                                                Descanso = false;

                                                                                //region Pomodoro 4
                                                                                if (NumeroActual > 3) {
                                                                                    Handler handler = new Handler();
                                                                                    handler.postDelayed(new Runnable() {
                                                                                        @Override
                                                                                        public void run() {
                                                                                            if(!Detenido) {
                                                                                                Estado.setText("A descansar - 5 minutos");
                                                                                                InicioChr = SystemClock.elapsedRealtime();
                                                                                                Descanso = true;

                                                                                                Handler handler = new Handler();
                                                                                                handler.postDelayed(new Runnable() {
                                                                                                    @Override
                                                                                                    public void run() {
                                                                                                        if(!Detenido) {
                                                                                                            ContadorActual = 4;
                                                                                                            Estado.setText("Pomodoro N° : 4 - " + TiempoActual + " minutos");
                                                                                                            InicioChr = SystemClock.elapsedRealtime();
                                                                                                            Descanso = false;

                                                                                                            Handler handler = new Handler();
                                                                                                            handler.postDelayed(new Runnable() {
                                                                                                                @Override
                                                                                                                public void run() {
                                                                                                                    Detener.setVisibility(View.GONE);
                                                                                                                    Estado.setText("¡Lo lograste! ahora necesitas descansar - 30 minutos");
                                                                                                                    Tiempo.setVisibility(View.GONE);
                                                                                                                    Comenzar.setVisibility(View.VISIBLE);
                                                                                                                    BotonAct = false;
                                                                                                                }
                                                                                                            }, TiempoActual * 60000);
                                                                                                        }

                                                                                                    }
                                                                                                }, 300000);
                                                                                            }
                                                                                        }
                                                                                    }, TiempoActual * 60000);
                                                                                }
                                                                                //endregion Pomodoro 4

                                                                                //region Final 3
                                                                                else {
                                                                                    Handler handler = new Handler();
                                                                                    handler.postDelayed(new Runnable() {
                                                                                        @Override
                                                                                        public void run() {
                                                                                            Estado.setText("A descansar - 5 minutos");
                                                                                            Tiempo.setVisibility(View.GONE);
                                                                                            Comenzar.setVisibility(View.VISIBLE);
                                                                                            BotonAct = false;
                                                                                        }
                                                                                    }, TiempoActual * 60000);
                                                                                }
                                                                                //endregion Final 3
                                                                            }

                                                                        }
                                                                    }, 300000);
                                                                }
                                                            }
                                                        }, TiempoActual * 60000);
                                                    }
                                                    //endregion Pomodoro 3

                                                    //region Final 2
                                                    else {
                                                        Handler handler = new Handler();
                                                        handler.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                Estado.setText("A descansar - 5 minutos");
                                                                Tiempo.setVisibility(View.GONE);
                                                                Comenzar.setVisibility(View.VISIBLE);
                                                                BotonAct = false;
                                                            }
                                                        }, TiempoActual * 60000);
                                                    }
                                                    //endregion Final 2
                                                }
                                            }
                                        }, 300000);
                                    }
                                }
                            }, TiempoActual*60000 - savedInstanceState.getLong("ContinuarChr"));
                        }

                    }
                    //endregion Pomodoro 2

                    else {
                        FinalizarAlarma(savedInstanceState);
                    }
                }
                //endregion Continuar 1° pausa

                //region Continuar 2° pausa
                else if (ContadorActual == 2){
                    //region Pomodoro 2
                    if(NumeroActual >2){
                        if(Descanso){
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if(!Detenido) {
                                        ContadorActual = 3;
                                        Estado.setText("Pomodoro N° : 3 - " + TiempoActual + " minutos");
                                        InicioChr = SystemClock.elapsedRealtime();
                                        Descanso = false;

                                        //region Pomodoro 4
                                        if (NumeroActual > 3) {

                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if(!Detenido) {
                                                        Estado.setText("A descansar - 5 minutos");
                                                        InicioChr = SystemClock.elapsedRealtime();
                                                        Descanso = true;

                                                        Handler handler = new Handler();
                                                        handler.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                if(!Detenido) {
                                                                    ContadorActual = 4;
                                                                    Estado.setText("Pomodoro N° : 4 - " + TiempoActual + " minutos");
                                                                    Descanso = false;
                                                                    InicioChr = SystemClock.elapsedRealtime();

                                                                    Handler handler = new Handler();
                                                                    handler.postDelayed(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            Detener.setVisibility(View.GONE);
                                                                            Estado.setText("¡Lo lograste! ahora necesitas descansar - 30 minutos");
                                                                            Tiempo.setVisibility(View.GONE);
                                                                            Comenzar.setVisibility(View.VISIBLE);
                                                                            BotonAct = false;
                                                                        }
                                                                    }, TiempoActual * 60000);
                                                                }

                                                            }
                                                        }, 300000);
                                                    }
                                                }
                                            }, TiempoActual * 60000);
                                        }
                                        //endregion Pomodoro 4

                                        //region Final 3
                                        else {
                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Estado.setText("A descansar - 5 minutos");
                                                    Tiempo.setVisibility(View.GONE);
                                                    Comenzar.setVisibility(View.VISIBLE);
                                                    BotonAct = false;
                                                }
                                            }, TiempoActual * 60000);
                                        }
                                        //endregion Final 3
                                    }
                                }
                            }, 300000 - savedInstanceState.getLong("ContinuarChr"));
                        }
                        else {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if(!Detenido) {
                                        Estado.setText("A descansar - 5 minutos");
                                        InicioChr = SystemClock.elapsedRealtime();
                                        Descanso = true;

                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                if(!Detenido) {
                                                    ContadorActual = 3;
                                                    Estado.setText("Pomodoro N° : 3 - " + TiempoActual + " minutos");
                                                    InicioChr = SystemClock.elapsedRealtime();
                                                    Descanso = false;

                                                    //region Pomodoro 4
                                                    if (NumeroActual > 3) {

                                                        Handler handler = new Handler();
                                                        handler.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                if(!Detenido) {
                                                                    Estado.setText("A descansar - 5 minutos");
                                                                    InicioChr = SystemClock.elapsedRealtime();
                                                                    Descanso = true;

                                                                    Handler handler = new Handler();
                                                                    handler.postDelayed(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            if(!Detenido) {
                                                                                ContadorActual = 4;
                                                                                Estado.setText("Pomodoro N° : 4 - " + TiempoActual + " minutos");
                                                                                InicioChr = SystemClock.elapsedRealtime();
                                                                                Descanso = false;

                                                                                Handler handler = new Handler();
                                                                                handler.postDelayed(new Runnable() {
                                                                                    @Override
                                                                                    public void run() {
                                                                                        Detener.setVisibility(View.GONE);
                                                                                        Estado.setText("¡Lo lograste! ahora necesitas descansar - 30 minutos");
                                                                                        Tiempo.setVisibility(View.GONE);
                                                                                        Comenzar.setVisibility(View.VISIBLE);
                                                                                        BotonAct = false;
                                                                                    }
                                                                                }, TiempoActual * 60000);
                                                                            }

                                                                        }
                                                                    }, 5000);
                                                                }
                                                            }
                                                        }, TiempoActual * 60000);
                                                    }
                                                    //endregion Pomodoro 4

                                                    //region Final 3
                                                    else {
                                                        Handler handler = new Handler();
                                                        handler.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                Estado.setText("A descansar - 5 minutos");
                                                                Tiempo.setVisibility(View.GONE);
                                                                Comenzar.setVisibility(View.VISIBLE);
                                                                BotonAct = false;
                                                            }
                                                        }, TiempoActual * 60000);
                                                    }
                                                    //endregion Final 3
                                                }
                                            }
                                        }, 300000);
                                    }
                                }
                            }, TiempoActual*60000 - savedInstanceState.getLong("ContinuarChr"));
                        }

                    }
                    //endregion Pomodoro 2
                    else {
                        FinalizarAlarma(savedInstanceState);
                    }
                }
                //endregion Continuar 2° pausa

                //region Continuar 3° pausa
                else if (ContadorActual == 3){
                    //region Pomodoro 2
                    if(NumeroActual >3){

                        if(Descanso){
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if(!Detenido) {
                                        ContadorActual = 4;
                                        Estado.setText("Pomodoro N° : 4 - " + TiempoActual + " minutos");
                                        InicioChr = SystemClock.elapsedRealtime();
                                        Descanso = false;

                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Detener.setVisibility(View.GONE);
                                                Estado.setText("¡Lo lograste! ahora necesitas descansar - 30 minutos");
                                                Tiempo.setVisibility(View.GONE);
                                                Comenzar.setVisibility(View.VISIBLE);
                                                BotonAct = false;
                                            }
                                        }, TiempoActual * 60000);
                                    }

                                }
                            }, 300000 - savedInstanceState.getLong("ContinuarChr"));
                        }
                        else {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if(!Detenido) {
                                        Estado.setText("A descansar - 5 minutos");
                                        InicioChr = SystemClock.elapsedRealtime();
                                        Descanso = true;

                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                if(!Detenido) {
                                                    Detener.setVisibility(View.GONE);
                                                    ContadorActual = 4;
                                                    Estado.setText("Pomodoro N° : 4 - " + TiempoActual + " minutos");
                                                    InicioChr = SystemClock.elapsedRealtime();
                                                    Descanso = false;

                                                    Handler handler = new Handler();
                                                    handler.postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Estado.setText("¡Lo lograste! ahora necesitas descansar - 30 minutos");
                                                            Tiempo.setVisibility(View.GONE);
                                                            Comenzar.setVisibility(View.VISIBLE);
                                                            BotonAct = false;
                                                        }
                                                    }, TiempoActual * 60000);
                                                }

                                            }
                                        }, 300000);
                                    }
                                }
                            }, TiempoActual*60000 - savedInstanceState.getLong("ContinuarChr"));
                        }

                    }
                    //endregion Pomodoro 2
                    else {
                        FinalizarAlarma(savedInstanceState);
                    }
                }
                //endregion Continuar3° pausa

                //region Continuar 4° pausa
                else if (ContadorActual == 4){
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Detener.setVisibility(View.GONE);
                            Estado.setText("¡Lo lograste! ahora necesitas descansar - 30 minutos");
                            Tiempo.setVisibility(View.GONE);
                            Comenzar.setVisibility(View.VISIBLE);
                            BotonAct = false;
                        }
                    }, TiempoActual*60000 - savedInstanceState.getLong("ContinuarChr"));
                }
                //endregion Continuar 4° pausa
            }

        }
        //endregion Recuperación de estado de componentes, datos y alarmas en cambio de vista

    }

    /**
     * Último cronómetro de alarma
     * @param bundle Vista actual
     */
    public void FinalizarAlarma(Bundle bundle){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Estado.setText("A descansar - 5 minutos");
                Tiempo.setVisibility(View.GONE);
                Comenzar.setVisibility(View.VISIBLE);
                BotonAct = false;
            }
        }, TiempoActual*60000 - bundle.getLong("ContinuarChr"));
    }

    /**
     * Detener todas las alarmas
     * @param view Vista actual
     */
    public void DetenerCronometro(View view){

        //region Actualizar componentes actuales
        Estado.setText("");
        Tiempo.setVisibility(View.GONE);
        Comenzar.setVisibility(View.VISIBLE);
        BotonAct = false;
        //endregion Actualizar componentes actuales

        //region Detención de alarmas

        //Detener Alarma genérica
        PackageManager pm  = MenuPomodoro.this.getPackageManager();
        ComponentName componentName = new ComponentName(MenuPomodoro.this, Alarma.class);
        pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);

        //Detener Alarma final
        pm  = MenuPomodoro.this.getPackageManager();
        componentName = new ComponentName(MenuPomodoro.this, AlarmaFinal.class);
        pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);

        //Detener Alarma de descanso
        pm  = MenuPomodoro.this.getPackageManager();
        componentName = new ComponentName(MenuPomodoro.this, Empezar.class);
        pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
        //endregion Detención de alarmas

        //region Actualización de botones
        Detener.setVisibility(View.GONE);
        Detenido = true;
        Toast.makeText(this, "Pomodoro cancelado", Toast.LENGTH_SHORT).show();
        //endregion Actualización de botones
    }

    /**
     * Iniciar alarma
     * @param view Vista actual
     */
    public void ComenzarCronometro(View view){

        //region Detener alarmas actuales
        PackageManager pm  = MenuPomodoro.this.getPackageManager();

        //Detener Alarma genérica
        ComponentName componentName = new ComponentName(MenuPomodoro.this, Alarma.class);
        pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        //Detener Alarma final
        pm  = MenuPomodoro.this.getPackageManager();
        componentName = new ComponentName(MenuPomodoro.this, AlarmaFinal.class);
        pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        //Detener Alarma de descanso
        pm  = MenuPomodoro.this.getPackageManager();
        componentName = new ComponentName(MenuPomodoro.this, Empezar.class);
        pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
        //endregion Detener alarmas actuales

        //region Actualización de componentes y datos de vista
        BotonAct = true;
        NumeroActual = Integer.valueOf(Cantidad.getSelectedItem().toString());
        TiempoActual = Integer.valueOf(Minutos.getSelectedItem().toString());
        Comenzar.setVisibility(View.GONE);
        Tiempo.setVisibility(View.VISIBLE);
        Detener.setVisibility(View.VISIBLE);
        Estado.setText("Pomodoro N° : 1 - " + TiempoActual + " minutos");
        AlarmaActual = 1;
        ContadorActual = 1;
        InicioChr = SystemClock.elapsedRealtime();
        Alarma(TiempoActual);
        Detenido=false;
        //endregion Actualización de componentes y datos de vista

        //region Pomodoro 2
        if(NumeroActual >1){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(!Detenido){
                        AlarmaActual = 3;
                        Estado.setText("A descansar - 5 minutos");
                        InicioChr = SystemClock.elapsedRealtime();
                        EmpezarDescanso(5);
                        Descanso = true;

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(!Detenido) {
                                    AlarmaActual = 1;
                                    ContadorActual = 2;
                                    Estado.setText("Pomodoro N° : 2 - " + TiempoActual + " minutos");
                                    InicioChr = SystemClock.elapsedRealtime();
                                    Alarma(TiempoActual);
                                    Descanso = false;

                                    //region Pomodoro 3
                                    if (NumeroActual > 2) {
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                if(!Detenido) {
                                                    AlarmaActual = 3;
                                                    Estado.setText("A descansar - 5 minutos");
                                                    InicioChr = SystemClock.elapsedRealtime();
                                                    EmpezarDescanso(5);
                                                    Descanso = true;

                                                    Handler handler = new Handler();
                                                    handler.postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            if(!Detenido) {
                                                                AlarmaActual = 1;
                                                                ContadorActual = 3;
                                                                Estado.setText("Pomodoro N° : 3 - " + TiempoActual + " minutos");
                                                                InicioChr = SystemClock.elapsedRealtime();
                                                                Alarma(TiempoActual);
                                                                Descanso = false;

                                                                //region Pomodoro 4
                                                                if (NumeroActual > 3) {
                                                                    Handler handler = new Handler();
                                                                    handler.postDelayed(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            if(!Detenido) {
                                                                                AlarmaActual = 3;
                                                                                Estado.setText("A descansar - 5 minutos");
                                                                                InicioChr = SystemClock.elapsedRealtime();
                                                                                EmpezarDescanso(5);
                                                                                Descanso = true;

                                                                                Handler handler = new Handler();
                                                                                handler.postDelayed(new Runnable() {
                                                                                    @Override
                                                                                    public void run() {
                                                                                        if(!Detenido) {
                                                                                            AlarmaActual = 2;
                                                                                            ContadorActual = 4;
                                                                                            Estado.setText("Pomodoro N° : 4 - " + TiempoActual + " minutos");
                                                                                            InicioChr = SystemClock.elapsedRealtime();
                                                                                            AlarmaFinal(TiempoActual);
                                                                                            Descanso = false;

                                                                                            Handler handler = new Handler();
                                                                                            handler.postDelayed(new Runnable() {
                                                                                                @Override
                                                                                                public void run() {
                                                                                                    Detener.setVisibility(View.GONE);
                                                                                                    Estado.setText("¡Lo lograste! ahora necesitas descansar - 30 minutos");
                                                                                                    Tiempo.setVisibility(View.GONE);
                                                                                                    Comenzar.setVisibility(View.VISIBLE);
                                                                                                    BotonAct = false;
                                                                                                }
                                                                                            }, TiempoActual * 60000);
                                                                                        }

                                                                                    }
                                                                                }, 300000);
                                                                            }
                                                                        }
                                                                    }, TiempoActual * 60000);
                                                                }
                                                                //endregion Pomodoro 4

                                                                //region Final 3
                                                                else {
                                                                    Handler handler = new Handler();
                                                                    handler.postDelayed(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            Estado.setText("A descansar - 5 minutos");
                                                                            Tiempo.setVisibility(View.GONE);
                                                                            Comenzar.setVisibility(View.VISIBLE);
                                                                            BotonAct = false;
                                                                        }
                                                                    }, TiempoActual * 60000);
                                                                }
                                                                //endregion Final 3
                                                            }

                                                        }
                                                    }, 300000);
                                                }
                                            }
                                        }, TiempoActual * 60000);
                                    }
                                    //endregion Pomodoro 3

                                    //region Final 2
                                    else {
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Estado.setText("A descansar - 5 minutos");
                                                Tiempo.setVisibility(View.GONE);
                                                Comenzar.setVisibility(View.VISIBLE);
                                                BotonAct = false;
                                            }
                                        }, TiempoActual * 60000);
                                    }
                                    //endregion Final 2
                                }
                            }
                        }, 300000);
                    }

                }
            }, TiempoActual*60000);
        }
        //endregion Pomodoro 2

        //region Pomodoro 1
        else{
            final Handler handler2 = new Handler();
            final Runnable r = new Runnable() {
                public void run() {
                    Estado.setText("A descansar - 5 minutos");
                    Tiempo.setVisibility(View.GONE);
                    Comenzar.setVisibility(View.VISIBLE);
                    BotonAct = false;
                    handler2.postDelayed(this, TiempoActual*60000);
                }
            };
            handler2.postDelayed(r, TiempoActual*60000);
        }
        //endregion Pomodoro 1
    }

    /**
     * Guardar datos y estado de componentes
     * @param outState Estado actual de la actividad
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {

        //region Guardado de datos y estados de los componentes
        outState.putInt("NumeroActual", NumeroActual);
        outState.putInt("Minutos", TiempoActual);
        outState.putBoolean("Cronometrado",BotonAct);
        outState.putString("Leyenda",Estado.getText().toString());
        outState.putInt("ContadorActual",ContadorActual);
        outState.putLong("INICIAL",InicioChr);
        outState.putLong("ContinuarChr",SystemClock.elapsedRealtime() - InicioChr);
        outState.putBoolean("DETENIDO",Detenido);
        outState.putBoolean("Descanso",Descanso);
        //endregion Guardado de datos y estados de los componentes

        super.onSaveInstanceState(outState);
    }

    /**
     * Inicia la alarma genérica
     * @param minutos Número de minutos de descanso
     */
    public void Alarma(int minutos){
        //Inicialización y designación de alarma
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //Inicialización y designación de nuevo intent
        Intent myIntent = new Intent(this, Alarma.class);

        //Inicialización y designación de nuevo pendingintent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MenuPomodoro.this, 1, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Encendido de alarma
        manager.set( AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 60000*minutos, pendingIntent);

    }

    /**
     * Inicia la alarma para el último pomodoro
     * @param minutos Número de minutos de descanso
     */
    public void AlarmaFinal(int minutos){
        //Inicialización y designación de alarma
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //Inicialización y designación de nuevo intent
        Intent myIntent = new Intent(MenuPomodoro.this, AlarmaFinal.class);

        //Inicialización y designación de nuevo pendingintent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MenuPomodoro.this, 1, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Encendido de alarma
        manager.set( AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 60000*minutos, pendingIntent);

    }

    /**
     * Inicia la alarma para descansar 5 minutos
     * @param minutos Número de minutos de descanso
     */
    public void EmpezarDescanso(int minutos){
        //Inicialización y designación de alarma
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //Inicialización y designación de nuevo intent
        Intent myIntent = new Intent(this, Empezar.class);

        //Inicialización y designación de nuevo pendingintent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MenuPomodoro.this, 1, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Encendido de alarma
        manager.set( AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 60000*minutos, pendingIntent);
    }

}