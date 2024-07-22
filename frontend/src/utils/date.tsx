export function formatDate(date: Date): string {
  return (
    date.getFullYear() +
    "." +
    addZeroIfSingleDigit(date.getMonth() + 1) +
    "." +
    addZeroIfSingleDigit(date.getDate()) +
    " " +
    date.getHours() +
    ":" +
    date.getMinutes()
  );
}

export function addZeroIfSingleDigit(num: number): string {
  return num < 10 ? "0" + num : num.toString();
}
