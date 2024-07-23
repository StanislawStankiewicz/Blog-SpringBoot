// export function formatDate(date: Date): string {
//   return (
//     date.getFullYear() +
//     "." +
//     addZeroIfSingleDigit(date.getMonth() + 1) +
//     "." +
//     addZeroIfSingleDigit(date.getDate()) +
//     " " +
//     addZeroIfSingleDigit(date.getHours()) +
//     ":" +
//     addZeroIfSingleDigit(date.getMinutes())
//   );
// }

export function formatDate(date: Date): string {
  return (
    date.getHours() +
    ":" +
    addZeroIfSingleDigit(date.getMinutes()) +
    " " +
    addZeroIfSingleDigit(date.getDate()) +
    "." +
    addZeroIfSingleDigit(date.getMonth() + 1) +
    "." +
    date.getFullYear()
  );
}

export function addZeroIfSingleDigit(num: number): string {
  return num < 10 ? "0" + num : num.toString();
}
