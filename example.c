int func(int x){
    int n, s=0;
    int i = 0;
    if(x<0){
        n = 0;
    }
    else{
        n = 1;
    }
    while(n<10){
        s = s + n;
        n = n + 1;
    }
    for(i=0;i<10;i=i+1){
        return 0;
    }
    return s;
}

int func2(int x, int y){
    int n, s=0;
    if(x<0){
        n = 0;
    }
    else{
        n = 1;
    }
    while(n<10){
        s = s + n;
        n = n + 1;
    }
    return s;
}