class PerformanceChecker:
    def __init__(self, ticks_per_second, players_online, events_per_player_per_tick):
        self.ticks_per_second = ticks_per_second
        self.players_online = players_online
        self.events_per_player_per_tick = events_per_player_per_tick

    def check_performance(self, ops):
        print("Operations: ", ops)
        events_per_second = ops
        total_events_ps = self.players_online * self.events_per_player_per_tick * self.ticks_per_second
        is_fast_enough = total_events_ps <= events_per_second
        max_players = ops / (self.events_per_player_per_tick * self.ticks_per_second)

        print("Events/s: ", events_per_second)
        print("Total Events Fired/s: ", total_events_ps)
        print("Fast Enough: ", is_fast_enough)
        print("Max Players: ", max_players)
        print()


if __name__ == "__main__":
    checker = PerformanceChecker(ticks_per_second=20, players_online=1000, events_per_player_per_tick=1)
    checker.check_performance(14.760e6)
    checker.check_performance(16.133e6)
    checker.check_performance(13.1e6)
    checker.check_performance(9e6)
    checker.check_performance(6e6)