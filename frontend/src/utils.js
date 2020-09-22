export function getCurrentDate() {
  let newDate = new Date();
  let date = newDate.getDate();
  let month = newDate.getMonth() + 1;
  let year = newDate.getFullYear();
  let hours = newDate.getHours();
  let min = newDate.getMinutes();
  let sec = newDate.getSeconds();

  return `${year}-${
    month < 10 ? `0${month}` : `${month}`
  }-${date} ${hours}:${min}:${sec}`;
}
