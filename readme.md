## Permission check를 해야할 때 main에 붙이고 수정하여 사용하자 

``` java

        // 버전체크해서 마시멜로우보다 낮으면 런타임 권한 체크를 하지 않는다. 이부분은 따로 메소드로 만들어도 좋다. 
        // 이전 사용은 onCreate 에서 했었다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        } else {
            // 체크 이후 실행하는 메소드
            loadData();
        }
    }

    // 여기서 코드는 임의로 지정해 준 숫자이다.
    private final int REQ_CODE = 100;

    // 1. 권한체크
    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {
        // 1.1 런타임 권한체크 (권한을 추가할때 1.2 목록작성과 2.1 권한체크에도 추가해야한다.)
        if ( checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            // 1.2 요청할 권한 목록 작성
            String permArr[] = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE, Manifest.permission.READ_CONTACTS};

            // 1.3 시스템에 권한요청
            requestPermissions(permArr, REQ_CODE);

        } else {
            loadData();
        }
    }

    // 2. 권한체크 후 콜백 - 사용자가 확인 후 시스템이 호출하는 함수
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if( requestCode == REQ_CODE) {
            // 2.1 배열에 넘긴 런타임 권한을 체크해서 승인이 됐으면
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                // 2.2 프로그램 실행
                loadData();
            } else {
                Toast.makeText(this, "권한을 허용하지 않으시면 프로그램을 실행할 수 없습니다.", Toast.LENGTH_SHORT).show();
                // 선택 : 1 종료, 2 권한체크 다시물어보기 할수도 있다. 일단은 끝내기
                finish();
            }
        }
    }

```