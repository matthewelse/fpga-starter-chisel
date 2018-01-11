all: output_files/toplevel.sof

clean:
	rm -rf output_files db hps_isw_handoff hps_sdram_p0_summary.csv HPS.sopcinfo incremental_db

program: starter.cdf output_files/toplevel.sof
	@echo "PGM toplevel.sof"
	@quartus_pgm -c DE-SoC starter.cdf > program.log 2>&1	

# This generates the HPS folder, as well as HPS.sopcinfo
HPS/synthesis/HPS.qip: HPS.qsys
	@echo "GEN HPS.qsys"
	@qsys-generate HPS.qsys --synthesis=verilog > qsys.log 2>&1

db/toplevel.map.qmsg: HPS/synthesis/HPS.qip
	@echo "MAP"
	@quartus_map --read_settings_files=on --write_settings_files=off starter -c toplevel > map.log 2>&1

db/toplevel.fit.qmsg: db/toplevel.map.qmsg
	@echo "FIT"
	@quartus_fit --read_settings_files=off --write_settings_files=off starter -c toplevel > fit.log 2>&1

db/toplevel.asm.qmsg: db/toplevel.fit.qmsg
	@echo "ASM"
	@quartus_asm --read_settings_files=off --write_settings_files=off starter -c toplevel > asm.log 2>&1

db/toplevel.sta.qmsg output_files/toplevel.sof: db/toplevel.asm.qmsg
	@echo "STA"
	@quartus_sta starter -c toplevel > sta.log 2>&1

