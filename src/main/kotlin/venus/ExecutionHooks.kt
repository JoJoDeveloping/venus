package venus

import venusbackend.simulator.plugins.SimulatorPlugin
import venusbackend.simulator.diffs.*
import venusbackend.simulator.Simulator
import venusbackend.riscv.MachineCode

class ExecutionHooks : SimulatorPlugin {
    override fun init(sim: Simulator) {}
    override fun finish(sim: Simulator, any: Any?): Any? { return 0 }
    override fun reset(sim: Simulator) {}

    override fun onStep(sim: Simulator, inst: MachineCode, prevPC: Number) {
        for (d in sim.postInstruction) {
            if (d is RegisterDiff) {
                IRenderer.getRenderer().updateRegister(d.id, d.v)
            }
        }
    }
}