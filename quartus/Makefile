all: output_files/toplevel.sof

clean:
	rm -rf output_files db hps_isw_handoff hps_sdram_p0_summary.csv HPS.sopcinfo incremental_db

program: starter.cdf output_files/toplevel.sof
	quartus_pgm -c DE-SoC starter.cdf	

# This generates the HPS folder, as well as HPS.sopcinfo
HPS/synthesis/HPS.qip: HPS.qsys
	qsys-generate HPS.qsys --synthesis=verilog

db/toplevel.map.qmsg: HPS/synthesis/HPS.qip
	quartus_map --read_settings_files=on --write_settings_files=off starter -c toplevel

db/toplevel.fit.qmsg: db/toplevel.map.qmsg
	quartus_fit --read_settings_files=off --write_settings_files=off starter -c toplevel

db/toplevel.asm.qmsg: db/toplevel.fit.qmsg
	quartus_asm --read_settings_files=off --write_settings_files=off starter -c toplevel

db/toplevel.sta.qmsg output_files/toplevel.sof: db/toplevel.asm.qmsg
	quartus_sta starter -c toplevel
