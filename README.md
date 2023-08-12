<img title="" src="https://github.com/tnwlsgk1002/habit-tracker-app/assets/76458724/0abacc29-4c42-4a9e-896b-b9b6018c3107" alt="image" data-align="center" width="20%" height="20%">

> 습관 추적기 앱을 통해 습관을 만들고, 기록하세요 ✏️

## 📱 ScreenShot.

| ![홈 화면](https://github.com/tnwlsgk1002/habit-tracker-app/assets/76458724/678f6f31-4927-48e9-8b55-b08f43f170ad) | ![습관 추가](https://github.com/tnwlsgk1002/habit-tracker-app/assets/76458724/c25c98bd-f0ee-4018-bee5-144c6534eb34) | ![상세 화면](https://github.com/tnwlsgk1002/habit-tracker-app/assets/76458724/8f89cdb5-fde2-4c69-a169-dc6cb108f83d) | ![image](https://github.com/tnwlsgk1002/tnwlsgk1002/assets/76458724/262ed322-9f85-47d3-bab4-8d2aafd4a9ca) |
| --- | --- | --- | --- |
| `홈 화면` | `습관 추가` | `습관 상세` | `다크 모드` |

</br>

## Learn.

### 🦾 Android CI

`Github Hooks`와 `Github Actions`를 사용해 Android CI를 적용했습니다.

* commit 전 `ktLint`, main branch에 merge 전 `detektLint`를 검사하여 코드의 퀄리티를 높이고, 잠재적인 버그를 식별할 수 있었습니다.
* 그 밖에 빌드 및 테스트를 자동화하여 main branch에 안정적인 코드를 두었습니다.
* blog Link: [Git Hooks와 Git Actions로 Android CI 구축하기](https://sjevie.tistory.com/entry/%EC%A0%81%EC%9A%A9%EA%B8%B0-Git-Hooks%EA%B3%BC-Git-Actions%EB%A1%9C-Android-CI-%EA%B5%AC%EC%B6%95%ED%95%98%EA%B8%B0)
* Issue Link: [settting: CI/CD 설정](https://github.com/tnwlsgk1002/habit-tracker-app/issues/5), [setting: detekt.yml 파일 추가 및 리팩토링](https://github.com/tnwlsgk1002/habit-tracker-app/issues/40)

### 📆 주간 캘린더 구현

`ViewPager2`를 사용해서 양쪽으로 스와이프가 가능한 주간 캘린더를 만들었습니다.

* Issue Link
  * [✨ feat: 홈 화면의 달력 선택 구현(DateView, DateItem 구현)](https://github.com/tnwlsgk1002/habit-tracker-app/issues/9)
  * [✨ feat: 홈 화면에 DateView 선택 및 이동 구현](https://github.com/tnwlsgk1002/habit-tracker-app/issues/32)

### 🏠 Room을 사용해 객체 간 관계 정의

Room을 사용해서 1:1, 1:N 관계를 정의하고 필요한 데이터를 join하여 사용하였습니다.

* `foreignKeys` 항목을 활용해서 관계를 정의했고, join 시에 `@Transaction`을 사용하였습니다.

### ⏰ AlarmManager 사용해서 특정 요일, 특정 시간에 알림

`AlarmManager`를 사용해서 특정 요일, 특정 시간에 `Notification`을 띄우도록 했습니다.

* 그 과정에서 Notification, PendingIntent, Doze 모드와 관련하여 학습한 후, 버전에 따라 대응하였습니다.
* blog Link: [Android Notification + PendingIntent](https://sjevie.tistory.com/entry/TIL%EA%B0%9C%EB%85%90-Android-Notification-PendingIntent) , [[fix] AlarmManager와 Doze 모드](https://sjevie.tistory.com/entry/fix-AlarmManager%EC%99%80-Doze-%EB%AA%A8%EB%93%9C)
* Issue Link: [✨ feat: 알람 구현](https://github.com/tnwlsgk1002/habit-tracker-app/issues/44), [🐞 bug: 다른 시간에 알람이 울리는 버그 수정 · Issue #47 · tnwlsgk1002/habit-tracker-app · GitHub](https://github.com/tnwlsgk1002/habit-tracker-app/issues/47)
